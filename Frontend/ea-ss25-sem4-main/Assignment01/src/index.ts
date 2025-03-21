// TypeScript Interface für Nachrichten
type MessageObject = {
    message: string;
    subject: string;
    read: boolean;
};

// Array für gespeicherte Nachrichten
const messageList: MessageObject[] = [];

// HTML-Elemente holen
const form = document.getElementById("form") as HTMLFormElement;
const subjectInput = document.getElementById("subject") as HTMLInputElement;
const messageInput = document.getElementById("message") as HTMLInputElement;
const messageListElement = document.getElementById("messageList") as HTMLUListElement;

// Funktion zum Erstellen einer Nachricht in der UI
function renderMessages() {
    messageListElement.innerHTML = ""; // Löscht alte Liste
    messageList.forEach((msg, index) => {
        const li = document.createElement("li");
        li.innerHTML = `<strong>${msg.subject}</strong>: ${msg.message}`;
        li.classList.add(msg.read ? "read" : "unread"); // ✅ CSS-Klasse setzen

        li.addEventListener("click", () => {
            messageList[index].read = true;
            renderMessages(); // Neu rendern
        });

        messageListElement.appendChild(li);
    });
}

// Event-Listener für Formular-Submit
form.addEventListener("submit", (event) => {
    event.preventDefault(); // Verhindert das Neuladen der Seite

    // Werte aus Input-Feldern holen
    const subject = subjectInput.value.trim();
    const message = messageInput.value.trim();

    if (!subject || !message) return; // Falls leer, nichts machen

    // Neue Nachricht erstellen
    const newMessage: MessageObject = {
        subject,
        message,
        read: false
    };

    // Nachricht zur Liste hinzufügen
    messageList.unshift(newMessage);

    // UI aktualisieren
    renderMessages();

    // Formular zurücksetzen
    form.reset();
});