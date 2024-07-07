import React from "react";
import { Alert } from "react-bootstrap";

export const Error: React.FC<{error: string}> = ({ error }) => (
    <Alert variant="danger">{error}</Alert>
);