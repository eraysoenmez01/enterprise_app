import { Me } from "@/types";
import { API_URL } from '@/config';

const URL = API_URL + '/users/me';


export default async function Header() {
    const response = await fetch(URL);
    console.log(response.status)

    const data = (await response.json()) as Me;

    return (
        <nav className="flex items-center justify-between w-full h-20 bg-gray-100 px-8 shadow text-black">
            <h1 className="text-2xl font-bold">Arrrbnb</h1>
            <div className="flex items-center gap-4">
                <button className="font-semibold hover:underline">Cabins</button>
                <button className="font-semibold hover:underline">Add cabin</button>
            </div>
            <div className="flex items-center gap-3">
                <img
                    src="https://i.pravatar.cc/300?img=35"
                    alt="avatar"
                    className="w-10 h-10 rounded-full"
                />
                <span className="font-medium">{data.firstName}</span>
            </div>
        </nav>
    );
}