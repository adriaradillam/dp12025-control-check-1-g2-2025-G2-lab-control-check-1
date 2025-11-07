import { within, waitFor } from '@testing-library/react';
import { rest } from 'msw';
import { server } from '../mocks/server';
import { render, screen } from "../test-utils.jsx";
import AttemptsListing from "./index.js";

const mockAttempts = [
  {
    id: 300,
    name: "player 1 - Mate in 1 attempt",
    start: "2025-10-20T12:00:00Z",
    finish: null,
    player: { username: "player1" },
    problem: { name: "Mate in 1", difficultyLevel: 1, goal: "checkmate in 1" }
  },
  {
    id: 301,
    name: "player 2 - Endgame try",
    start: "2025-10-21T09:30:00Z",
    finish: "2025-10-21T10:10:00Z",
    player: { username: "player2" },
    problem: { name: "Mate in 2", difficultyLevel: 3, goal: "checkmate in 2" }
  },
];

const getDataRows = (table) => {
  const allRows = within(table).getAllByRole("row");
  console.log("All rows count:", allRows.length);
  var dataRows = allRows.filter(
    (row) => within(row).queryAllByRole("columnheader").length === 0
  );
  console.log("Filterd rows count:", allRows.length);
  return dataRows;
};

function expectDateEqual(cell, expectedIso) {
  const cellDate = new Date(cell.textContent);
  const expectedDate = new Date(expectedIso);
  expect(cellDate.toDateString()).toBe(expectedDate.toDateString());
}

describe('Basic lisitng of attempts', () => {
  beforeEach(() => {
    server.use(
      rest.get('*/api/v1/attempts', (_req, res, ctx) =>
        res(ctx.status(200), ctx.json(mockAttempts))
      )
    )
  });

  test('renders a table with data rows from the API (as many rows as mock data available)', async () => {
    render(<AttemptsListing />);

    // Wait until the table is in the DOM
    const table = await screen.findByRole("table");

    await waitFor(() => {
      expect(screen.getByText(/player 1 - Mate in 1 attempt/i)).toBeInTheDocument();
    });

    // Filter out header rows — those containing “Name” or “Start”
    const dataRows = getDataRows(table);
    // Should have one row per match in mock data
    expect(dataRows.length).toBe(mockAttempts.length);
  });

  test("renders a table with the data of the first mock attempt", async () => {
    render(<AttemptsListing />);
    // Wait until the table is in the DOM
    const table = await screen.findByRole("table");
    await waitFor(() => {
      expect(screen.getByText(/player 1 - Mate in 1 attempt/i)).toBeInTheDocument();
    });

    // Filter out header rows — those containing “Name” or “Start”
    const dataRows = getDataRows(table);

    // Should have one row per match in mock data
    expect(dataRows.length).toBe(mockAttempts.length);

    // Verify first row (basic data presence)
    const firstRow = dataRows[0];
    const cells = within(firstRow).getAllByRole("cell");

    // 0 Name, 1 Start, 2 Finish, 3 Player, 4 Problem name, 5 Problem difficulty
    expect(cells[0]).toHaveTextContent(new RegExp(mockAttempts[0].name, "i"));
    expectDateEqual(cells[1], mockAttempts[0].start);   // Start
    expect(cells[2]).toHaveTextContent("");
    expect(cells[3]).toHaveTextContent(mockAttempts[0].player.username);
    expect(cells[4]).toHaveTextContent(mockAttempts[0].problem.name);
  });

  test("renders a table with the data of the last mock attempt", async () => {
    render(<AttemptsListing />);
    // Wait until the table is in the DOM
    const table = await screen.findByRole("table");
    await waitFor(() => {
      expect(screen.getByText(/player 1 - Mate in 1 attempt/i)).toBeInTheDocument();
    });


    // Filter out header rows — those containing “Name” or “Start”
    const dataRows = getDataRows(table);

    // Should have one row per match in mock data
    expect(dataRows.length).toBe(mockAttempts.length);

    // Verify second row
    const secondRow = dataRows[1];
    // Get all <td> cells in order
    const cells = within(secondRow).getAllByRole("cell");

    // 0 Name, 1 Start, 2 Finish, 3 Player, 4 Problem name, 5 Problem difficulty
    expect(cells[0]).toHaveTextContent(new RegExp(mockAttempts[1].name, "i"));
    expectDateEqual(cells[1], mockAttempts[1].start);   // Start
    expectDateEqual(cells[1], mockAttempts[1].finish);   // Finish
    expect(cells[3]).toHaveTextContent(mockAttempts[1].player.username);
    expect(cells[4]).toHaveTextContent(mockAttempts[1].problem.name);
    expect(cells[5]).toHaveTextContent(mockAttempts[1].problem.difficultyLevel.toString());
  });
});

describe('Empty listing of attempts', () => {
  beforeEach(() => {
    server.use(
      rest.get('*/api/v1/attempts', (_req, res, ctx) =>
        res(ctx.status(200), ctx.json([]))
      )
    )
  });
  
  test('renders NO ATTEMPTS when API returns empty list', async () => {
    render(<AttemptsListing />);
    const noAttemptsHeader = await screen.findByRole("heading", { name: /NO ATTEMPTS/i });
    expect(noAttemptsHeader).toBeInTheDocument();
  });
});
