"use server";

import { API_URL } from "@/config";

export async function createRoom( room: {
  title: string;
  description: string;
  heroUrl: string;
  pricePerNight: { amount: number; currency: string };
}) {
  const response = await fetch(`${API_URL}/rooms`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      title: "Test Room",
      description: "Just a test room",
      heroUrl: "https://c.pxhere.com/photos/2f/03/room_window_light-865295.jpg!d",
      pricePerNight: { amount: 100, currency: "USD" },
    }),
  });

  console.log("Server response:", response.status);
}