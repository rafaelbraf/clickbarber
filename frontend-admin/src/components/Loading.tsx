import React from "react";
import { Spinner } from "react-bootstrap";

export const Loading: React.FC <{ message: string, height?: string }> = ({ message, height = '100vh' }) => (
    <div className="d-flex flex-column justify-content-center align-items-center" style={{ height }}>
        <Spinner animation="border" />
        <p className="mt-3">{message}</p>
    </div>
);