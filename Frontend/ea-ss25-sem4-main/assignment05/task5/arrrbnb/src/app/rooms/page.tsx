import { API_URL } from "@/config";
import { Collection, Room } from "@/types";

const URL = API_URL + '/rooms';

export default async function Rooms() {
    const response = await fetch(URL);
    const data = (await response.json()) as Collection<Room>;
    
    console.log(data);

    return (
        <ul>
            {data.nodes.map((room) => (
                <li key={room.id}>
                    <h2>{room.title}</h2>
                    <p>{room.description}</p>
                    <img src={room.heroUrl}/>
                </li>
            ))}
        </ul>
    );
}