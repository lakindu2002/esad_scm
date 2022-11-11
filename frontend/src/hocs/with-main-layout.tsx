import React from "react";
import { MainLayout } from "../layouts/main-layout";

// eslint-disable-next-line react/display-name
export const withMainLayout = (Component: React.ComponentType) => (props: any) => (
    <MainLayout>
        <Component {...props} />
    </MainLayout>
)