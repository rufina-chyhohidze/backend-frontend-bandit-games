type FullScreenFrameProps = {
    src: string;
};

export function FullScreenFrame({ src }: FullScreenFrameProps) {
    return (
        <div
            style={{
                position: "fixed",
                top: 0,
                left: 0,
                width: "100vw",
                height: "100vh",
                margin: 0,
                padding: 0,
                background: "black",
                zIndex: 9999
            }}
        >
            <iframe
                src={src}
                style={{ width: "100%", height: "100%", border: 0 }}
            />
        </div>
    );
}
