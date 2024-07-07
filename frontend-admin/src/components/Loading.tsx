import React from "react";
import { Spinner } from "react-bootstrap";

export const Loading: React.FC <{ message: string }> = ({ message }) => (
    <div className="d-flex flex-column justify-content-center align-items-center" style={{ height: '100vh' }}>
        <Spinner animation="border" />
        <p className="mt-3">{message}</p>
    </div>
);