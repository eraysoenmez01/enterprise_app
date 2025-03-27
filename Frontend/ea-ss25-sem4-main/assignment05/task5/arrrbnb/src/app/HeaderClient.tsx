"use client";

import Link from "next/link";
import { useSelectedLayoutSegment } from "next/navigation";
import { Me } from "@/types";

export default function HeaderClient({ user }: { user: Me }) {
  const segment = useSelectedLayoutSegment();

  return (
    <nav className="flex items-center justify-between w-full h-20 bg-gray-100 px-8 shadow text-black">
      <h1 className="text-2xl font-bold">Arrrbnb</h1>

      <div className="flex items-center gap-4 font-semibold">
        <Link
          href="/rooms"
          className={`px-2 py-1 rounded ${
            segment === "rooms" ? "bg-gray-200 font-bold" : ""
          }`}
        >
          Cabins
        </Link>
        <Link
          href="/create"
          className={`px-2 py-1 rounded ${
            segment === "create" ? "bg-gray-200 font-bold" : ""
          }`}
        >
          Add cabin
        </Link>
      </div>

      <div className="flex items-center gap-3">
        <img
          src={user.portraitUrl}
          alt="avatar"
          className="w-10 h-10 rounded-full"
        />
        <span className="font-medium">{user.firstName}</span>
      </div>
    </nav>
  );
}