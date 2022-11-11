import { useRouter } from "next/router";
import React, { FC, useEffect, useState } from "react";
import LoginPage from "../../pages";
import { AuthResponse } from "../types/auth";

export const AuthGuard: FC<{ children: React.ReactNode }> = ({ children }) => {
    const [authenticated, setAuthenticated] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem("token");
        const parsedToken = (JSON.parse(token || '{}') as AuthResponse);
        if (parsedToken?.token && (Date.now() < parsedToken.expiresAt)) {
            setAuthenticated(true);
        } else {
            setAuthenticated(false);
        }
    }, []);
    if (authenticated) {
        return <>{children}</>;
    }
    return <LoginPage />;
};