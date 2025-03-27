import { describe, it, expect } from "vitest";
import { formatDate } from "../formatDate";

describe("formatDate", () => {
  it("formats a date correctly", () => {
    const result = formatDate("2024-06-14T12:00:00Z");
    expect(result).toBe("Jun 14, 2024");
  });

  it("returns empty string for invalid date", () => {
    const result = formatDate("not-a-date");
    expect(result).toBe("");
  });
});