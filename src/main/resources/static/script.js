const API_URL = "/entries";


// ==============================
// LOAD ENTRIES
// ==============================

async function loadEntries() {

    const table = document.getElementById("entriesTable");

    table.innerHTML = `
        <tr>
            <td colspan="6" class="loading">
                Loading entries...
            </td>
        </tr>
    `;

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Failed to load entries");
        }

        const entries = await response.json();

        displayEntries(entries);

        updateStats(entries);

    } catch (error) {

        console.error(error);

        table.innerHTML = `
            <tr>
                <td colspan="6" class="loading">
                    Could not connect to the API.
                </td>
            </tr>
        `;
    }
}


// ==============================
// DISPLAY ENTRIES
// ==============================

function displayEntries(entries) {

    const table = document.getElementById("entriesTable");

    if (!entries || entries.length === 0) {

        table.innerHTML = `
            <tr>
                <td colspan="6" class="loading">
                    No film entries found.
                </td>
            </tr>
        `;

        return;
    }


    table.innerHTML = entries.map(entry => {

        return `
            <tr>

                <td>
                    ${entry.id ?? "-"}
                </td>

                <td>
                    <strong>
                        ${escapeHtml(entry.title ?? "-")}
                    </strong>
                </td>

                <td>
                    ${escapeHtml(entry.teamName ?? "-")}
                </td>

                <td>
                    ${escapeHtml(entry.email ?? "-")}
                </td>

                <td>

                    ${
                        entry.videoUrl
                            ? `
                                <a
                                    class="video-link"
                                    href="${escapeAttribute(entry.videoUrl)}"
                                    target="_blank"
                                >
                                    Watch
                                </a>
                              `
                            : "-"
                    }

                </td>

                <td>

                    <button
                        class="action-btn delete-btn"
                        onclick="deleteEntry(${entry.id})"
                    >
                        Delete
                    </button>

                </td>

            </tr>
        `;

    }).join("");
}


// ==============================
// SUBMIT ENTRY
// ==============================

document
    .getElementById("entryForm")
    .addEventListener("submit", async function(event) {

        event.preventDefault();


        const message =
            document.getElementById("formMessage");


        const data = {

            title:
                document.getElementById("title").value.trim(),

            teamName:
                document.getElementById("teamName").value.trim(),

            email:
                document.getElementById("email").value.trim(),

            videoUrl:
                document.getElementById("videoUrl").value.trim()
        };


        try {

            const response = await fetch(API_URL, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(data)
            });


            if (!response.ok) {

                const errorText =
                    await response.text();

                throw new Error(
                    errorText || "Submission failed"
                );
            }


            message.textContent =
                "Film submitted successfully.";

            message.style.color = "#4ade80";


            document
                .getElementById("entryForm")
                .reset();


            await loadEntries();


        } catch (error) {

            console.error(error);

            message.textContent =
                "Could not submit the film.";

            message.style.color = "#f87171";
        }

    });


// ==============================
// DELETE ENTRY
// ==============================

async function deleteEntry(id) {

    const confirmed =
        confirm(
            `Are you sure you want to delete entry ${id}?`
        );


    if (!confirmed) {
        return;
    }


    try {

        const response = await fetch(
            `${API_URL}/${id}`,
            {
                method: "DELETE"
            }
        );


        if (!response.ok) {

            const errorText =
                await response.text();

            throw new Error(
                errorText || "Delete failed"
            );
        }


        alert("Entry deleted successfully.");

        await loadEntries();


    } catch (error) {

        console.error(error);

        alert(
            "Could not delete this entry.\n\n" +
            "If this entry has a score card, " +
            "the score card may need to be removed first."
        );
    }
}


// ==============================
// STATISTICS
// ==============================

function updateStats(entries) {

    document.getElementById("totalEntries")
        .textContent = entries.length;


    const teams = new Set(
        entries
            .map(entry => entry.teamName)
            .filter(Boolean)
    );


    document.getElementById("totalTeams")
        .textContent = teams.size;


    if (entries.length > 0) {

        document.getElementById("topFilm")
            .textContent =
                entries[0].title || "-";

    } else {

        document.getElementById("topFilm")
            .textContent = "-";
    }
}


// ==============================
// SECURITY HELPERS
// ==============================

function escapeHtml(value) {

    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}


function escapeAttribute(value) {

    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll('"', "&quot;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;");
}


// ==============================
// START APPLICATION
// ==============================

loadEntries();