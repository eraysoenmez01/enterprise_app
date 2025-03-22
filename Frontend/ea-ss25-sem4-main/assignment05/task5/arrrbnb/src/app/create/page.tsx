"use client";

import { useState } from "react";
import { createRoom } from "./actions";

export default function CreatePage() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [heroUrl, setHeroUrl] = useState("");
  const [amount, setAmount] = useState(0);

  async function handleSubmit(formData: FormData) {
    const room = {
      title,
      description,
      heroUrl,
      pricePerNight: {
        amount,
        currency: "USD",
      },
    };
    await createRoom(room);
  }

  return (
    <form
  action={handleSubmit}
  className="max-w-md mx-auto bg-white p-6 rounded shadow-md flex flex-col gap-4 text-black"
>
  <h1 className="text-2xl font-bold mb-4 text-center">Add new room</h1>

  <input
    type="text"
    placeholder="Title"
    className="border border-gray-300 p-2 rounded placeholder:text-black"
    />

  <input
    type="text"
    placeholder="Description"
    className="border border-gray-300 p-2 rounded placeholder:text-black"
    />

  <input
    type="url"
    placeholder="Hero Image URL"
    className="border border-gray-300 p-2 rounded placeholder:text-black"
    />

  <input
    type="number"
    placeholder="Price per night (USD)"
    className="border border-gray-300 p-2 rounded placeholder:text-black"
    />

  <button
    type="submit"
    className="bg-blue-500 hover:bg-blue-600 text-white font-semibold px-4 py-2 rounded"
  >
    Submit Room
  </button>
</form>
  );
}