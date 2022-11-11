import React from "react";
import { AuthGuard } from "../guards/auth-guard";

export const withAuthGuard = (Component: React.ComponentType) => {
    // eslint-disable-next-line react/display-name
    return () => {
        return (
            <AuthGuard>
                <Component />
            </AuthGuard>
        );
    };
};