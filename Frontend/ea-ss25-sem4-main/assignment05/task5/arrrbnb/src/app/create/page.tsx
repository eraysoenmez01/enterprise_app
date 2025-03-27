"use client";

import { useActionState } from "react";
import { createRoom } from "./actions";

const initialState = { error: "" };

export default function CreatePage() {
  const [state, formAction, isPending] = useActionState(createRoom, initialState);

  return (
    <form
      action={formAction}
      className="max-w-md mx-auto bg-white p-6 rounded shadow-md flex flex-col gap-4 text-black"
    >
      <h1 className="text-2xl font-bold text-center mb-6">Add cabin</h1>

      <div className="flex flex-col gap-1">
        <label htmlFor="title" className="font-medium">Title</label>
        <input
          name="title"
          id="title"
          type="text"
          placeholder="e.g. Cozy Treehouse"
          className="border border-gray-300 p-2 rounded"
          disabled={isPending}
          required
        />
      </div>

      <div className="flex flex-col gap-1">
        <label htmlFor="description" className="font-medium">Description</label>
        <input
          name="description"
          id="description"
          type="text"
          placeholder="Write something nice"
          className="border border-gray-300 p-2 rounded"
          disabled={isPending}
          required
        />
      </div>

      <div className="flex flex-col gap-1">
        <label htmlFor="heroUrl" className="font-medium">Hero URL from pxhere.com</label>
        <input
          name="heroUrl"
          id="heroUrl"
          type="url"
          placeholder="https://c.pxhere.com/photos/..."
          className="border border-gray-300 p-2 rounded"
          disabled={isPending}
          required
        />
      </div>

      <div className="flex flex-col gap-1">
        <label htmlFor="amount" className="font-medium">Price per night</label>
        <div className="flex items-center gap-2">
          <input
            name="amount"
            id="amount"
            type="number"
            placeholder="0"
            className="border border-gray-300 p-2 rounded w-full"
            disabled={isPending}
            required
          />
          <span className="text-sm font-semibold">USD</span>
        </div>
      </div>

      {state.error && <p className="text-red-500 text-sm">{state.error}</p>}

      <button
        type="submit"
        disabled={isPending}
        className="bg-blue-500 hover:bg-blue-600 text-white font-semibold px-4 py-2 rounded mt-4 disabled:opacity-50"
      >
        {isPending ? "Submitting..." : "Submit"}
      </button>
    </form>
  );
}