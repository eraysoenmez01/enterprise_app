/// <reference types="vitest" />

import { render, screen } from "@testing-library/react";
import Link from "next/link";
import '@testing-library/jest-dom';

function Pagination({ totalPages, currentPage }: { totalPages: number; currentPage: number }) {
  return (
    <div>
      {Array.from({ length: totalPages }).map((_, index) => {
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
  );
}

describe("Pagination", () => {
  it("renders correct number of page links", () => {
    render(<Pagination totalPages={3} currentPage={2} />);

    // 3 Links: page 1, 2, 3
    expect(screen.getByText("1")).toBeInTheDocument();
    expect(screen.getByText("2")).toBeInTheDocument();
    expect(screen.getByText("3")).toBeInTheDocument();
  });

  it("highlights the current page", () => {
    render(<Pagination totalPages={3} currentPage={2} />);
    const active = screen.getByText("2");
    expect(active).toHaveClass("bg-blue-500");
  });

  it("has correct href attributes", () => {
    render(<Pagination totalPages={3} currentPage={1} />);
    expect(screen.getByText("1").closest("a")).toHaveAttribute("href", "?page=1");
    expect(screen.getByText("2").closest("a")).toHaveAttribute("href", "?page=2");
    expect(screen.getByText("3").closest("a")).toHaveAttribute("href", "?page=3");
  });
});