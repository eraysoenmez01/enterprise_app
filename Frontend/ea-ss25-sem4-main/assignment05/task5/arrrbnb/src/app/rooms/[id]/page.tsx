import { API_URL } from "@/config";
import { Room } from "@/types";
import { notFound } from "next/navigation";

type RoomPageProps = {
  params: { id: string };
};

export default async function RoomPage({ params }: RoomPageProps) {
  const response = await fetch(`${API_URL}/rooms/${params.id}`);

  if (!response.ok) return notFound();

  const room = (await response.json()) as Room;

  return (
    <div className="max-w-3xl mx-auto p-6 space-y-4">
      <img src={room.heroUrl} alt={room.title} className="w-full h-64 object-cover rounded" />
      <h1 className="text-2xl font-bold text-black">{room.title}</h1>
      <p className="text-gray-700">{room.description}</p>
      <p className="text-gray-500 text-sm">
        Added on{" "}
        {new Date(room.createdAt).toLocaleDateString("en-US", {
          month: "short",
          day: "numeric",
          year: "numeric",
        })}
      </p>
      <p className="text-emerald-600 font-semibold">
        ${room.pricePerNight.amount} <span className="text-gray-500 text-sm">/ day</span>
      </p>
      <div className="flex items-center gap-2">
        <img src={room.owner.portraitUrl} alt={room.owner.firstName} className="w-8 h-8 rounded-full" />
        <span className="text-gray-800">{room.owner.firstName}</span>
      </div>
    </div>
  );
}

export async function generateMetadata({ params }: RoomPageProps) {
    const response = await fetch(`${API_URL}/rooms/${params.id}`);
    if (!response.ok) return { title: "Not found" };
    const room = (await response.json()) as Room;
    return {
      title: room.title,
    };
  }