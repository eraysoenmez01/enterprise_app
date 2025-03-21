import { useRef, useState } from "react";
import "./index.css"; 

export default function App() {
  const inputRef = useRef<HTMLInputElement>(null); // Referenz auf das Eingabefeld
  const [messages, setMessages] = useState<string[]>([]); // Zugriff auf ein HTML-Element bekommen, State für alle Nachrichten
  const [readMessages, setReadMessages] = useState<number[]>([]); // Ein Array mit Zahlen, also mit Indexes von gelesenen Nachrichten. State für gelesene Nachrichten (nur die indexes werden gespeichert)

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault(); // Verhindert Neuladen
    const form = event.currentTarget; // Zugriff auf das Formular
    const formData = new FormData(form); // Eingabedaten auslesen
    const newMessage = formData.get("todo") as string;
    
    if (newMessage.trim() !== "") {
      setMessages([...messages, newMessage]); // neue Nachricht speichern
      form.reset(); // Setzt das Formular zurück -> Input-Feld ist nach dem Absenden wieder leer
      inputRef.current?.focus(); // Der Cursor springt zurück ins Eingabefeld
    }
  }

  function handleClick(index: number) {
    if (readMessages.includes(index)) return; // Schon gelesen, dann nichts tun
    setReadMessages([...readMessages,index]);
  }

  return (
    <div>
      <h1>Message Board</h1>
      <form onSubmit={handleSubmit}>
        <input name="todo" ref={inputRef} required placeholder="Type something..."/>
        <button type="submit">Submit</button>
      </form>

      <ul id="messageList">
        {messages.map((msg, index) => (
          <li key={index} onClick={() => handleClick(index)} className={readMessages.includes(index) ? "read" : "unread"}>{msg}</li>
        ))}
      </ul>
      {messages.length > 0 && (
      <div id="messages">
        Du hast {messages.length} Nachrichten,{" "}
        {messages.length - readMessages.length} davon sind ungelesen.
      </div>
    )}
    </div>
  )



}