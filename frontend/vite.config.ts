import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
    plugins: [
        react({
            babel: {
                plugins: [
                    [
                        "babel-plugin-react-compiler",
                        {
                            compilationMode: "annotation", // 'all' | 'annotation' | 'infer'
                        },
                    ],
                ],
            },
        }),
    ],
    server: {
        proxy: {
            "/api": {
                target: "http://127.0.0.1:8083",
                changeOrigin: true,
            },
        },
    },
});
