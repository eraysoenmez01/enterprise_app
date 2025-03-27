import { API_URL } from "@/config";
import { Me } from "@/types";
import HeaderClient from "./HeaderClient";

const URL = API_URL + "/users/me";

export default async function Header() {
  const response = await fetch(URL);
  const data = (await response.json()) as Me;

  return <HeaderClient user={data} />;
}