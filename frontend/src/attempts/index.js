import React, { useEffect, useState } from "react";
import { Table } from "reactstrap";

export default function AttemptsListing() {
  const [attempts, setAttempts] = useState([]);

  useEffect(() => {
    fetch("/api/v1/attempts")
      .then((response) => response.json())
      .then((data) => setAttempts(data))
      .catch(() => setAttempts([]));
  }, []);

  if (attempts.length === 0) {
    return <h1>NO ATTEMPTS</h1>;
  }

  return (
    <Table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Start</th>
          <th>Finish</th>
          <th>Player</th>
          <th>Problem</th>
          <th>Difficulty</th>
        </tr>
      </thead>
      <tbody>
        {attempts.map((attempt) => (
          <tr key={attempt.id}>
            <td>{attempt.name}</td>
            <td>{attempt.start}</td>
            <td>{attempt.finish}</td>
            <td>{attempt.player?.username}</td>
            <td>{attempt.problem?.name}</td>
            <td>{attempt.problem?.difficultyLevel}</td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
