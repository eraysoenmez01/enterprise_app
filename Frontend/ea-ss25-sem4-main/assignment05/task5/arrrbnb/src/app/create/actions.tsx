"use server";

import { API_URL } from "@/config";
import { RoomInput } from "@/types";
import { redirect } from "next/navigation";

export async function createRoom(_: any, formData: FormData) {
  const title = formData.get("title") as string;
  const description = formData.get("description") as string;
  const heroUrl = formData.get("heroUrl") as string;
  const amount = Number(formData.get("amount"));

  if (!heroUrl.startsWith("https://c.pxhere.com/")) {
    return { error: "Image URL must start with https://c.pxhere.com/" };
  }

  const room: RoomInput = {
    title,
    description,
    heroUrl,
    pricePerNight: {
      amount,
      currency: "USD",
    },
  };

  const response = await fetch(`${API_URL}/rooms`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(room),
  });

  if (!response.ok) {
    return { error: "Room creation failed!" };
  }

  redirect("/rooms");
}