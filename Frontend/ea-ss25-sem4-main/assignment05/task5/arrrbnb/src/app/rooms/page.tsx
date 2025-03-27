import { API_URL } from "@/config";
import { Collection, Room } from "@/types";
import Link from "next/link";

export default async function Rooms({
  searchParams,
}: {
  searchParams: { page?: string };
}) {
  const currentPage = Number(searchParams.page ?? "1");
  const apiPage = currentPage - 1;
  const size = 9;

  const response = await fetch(`${API_URL}/rooms?page=${apiPage}&size=${size}`);
  const data = (await response.json()) as Collection<Room>;

  return (
    <div className="px-8 py-6">
      {/* Grid mit 3 Spalten */}
      <ul className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {data.nodes.map((room) => (
          <li key={room.id} className="...">
          <Link href={`/rooms/${room.id}`} className="block hover:opacity-90">
            <img src={room.heroUrl} alt={room.title} className="w-full h-48 object-cover" />
            <div className="p-4 space-y-2 text-black">
              <h2 className="font-semibold line-clamp-1">{room.title}</h2>
              <p className="text-gray-600 text-sm line-clamp-2">{room.description}</p>
              <p className="text-gray-400 text-xs">
                Added on {new Date(room.createdAt).toLocaleDateString("en-US", {
                  month: "short",
                  day: "numeric",
                  year: "numeric",
                })}
              </p>
        
              <div className="flex justify-between items-center pt-2">
                <p className="text-sm font-semibold text-emerald-600">
                  ${room.pricePerNight.amount}
                  <span className="text-gray-500 text-xs">/day</span>
                </p>
                <div className="flex items-center gap-2">
                  <img
                    src={room.owner.portraitUrl}
                    alt={room.owner.firstName}
                    className="w-6 h-6 rounded-full"
                  />
                  <span className="text-sm text-gray-800">{room.owner.firstName}</span>
                </div>
              </div>
            </div>
          </Link>
        </li>
        ))}
      </ul>

      {/* Pagination */}
      <div className="flex justify-center gap-2 mt-8">
        {Array.from({ length: data.page.totalPages }).map((_, index) => {
          const displayPage = index + 1;
          return (
            <Link
              key={index}
              href={`?page=${displayPage}`}
              className={`px-3 py-1 border rounded ${
                currentPage === displayPage
                  ? "bg-blue-500 text-white"
                  : "bg-white text-black"
              }`}
            >
              {displayPage}
            </Link>
          );
        })}
      </div>

      {/* Info unter Pagination */}
      <p className="text-center text-sm text-gray-500 mt-4">
        Page {currentPage} of {data.page.totalPages} ({data.page.totalElements} results in total)
      </p>
    </div>
  );
}