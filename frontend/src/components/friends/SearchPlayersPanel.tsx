import { Box, Button, CircularProgress, Divider, Stack, TextField, Typography } from "@mui/material";
import SearchRoundedIcon from "@mui/icons-material/SearchRounded";
import PersonAddRoundedIcon from "@mui/icons-material/PersonAddRounded";
import type { PlayerDtoWithName } from "../../models/friendship";

export function SearchPlayersPanel(props: {
    searchQuery: string;
    setSearchQuery: (v: string) => void;
    submittedQuery: string;
    onSubmit: (e: React.FormEvent) => void;
    loading: boolean;
    results: PlayerDtoWithName[];
    onAdd: (id: string) => void;
    addPending: boolean;
}) {
    const panelSx = {
        bgcolor: "rgba(255,255,255,0.035)",
        border: "1px solid rgba(255,255,255,0.08)",
        borderRadius: 3,
        p: 1.25,
    };

    const rowSx = {
        p: 1.1,
        borderRadius: 2.5,
        bgcolor: "rgba(255,255,255,0.045)",
        border: "1px solid rgba(255,255,255,0.08)",
    };

    const pillBtnSx = {
        borderRadius: 999,
        textTransform: "none" as const,
        px: 1.75,
        py: 0.65,
    };

    return (
        <Box sx={panelSx}>
            <Typography sx={{ color: "#fff", fontWeight: 800 }}>Find Players</Typography>
            <Divider sx={{ borderColor: "rgba(255,255,255,0.08)", my: 1.1 }} />

            <Box component="form" onSubmit={props.onSubmit}>
                <Stack direction="row" spacing={1}>
                    <TextField
                        value={props.searchQuery}
                        onChange={(e) => props.setSearchQuery(e.target.value)}
                        placeholder="Username..."
                        fullWidth
                        InputProps={{
                            startAdornment: <SearchRoundedIcon sx={{ mr: 1, color: "rgba(255,255,255,0.6)" }} />,
                        }}
                        sx={{
                            "& .MuiInputBase-root": {
                                bgcolor: "rgba(255,255,255,0.06)",
                                color: "#fff",
                                borderRadius: 2.5,
                                height: 40,
                            },
                            "& .MuiOutlinedInput-notchedOutline": {
                                borderColor: "rgba(255,255,255,0.14)",
                            },
                            "& .MuiInputBase-input::placeholder": {
                                color: "rgba(255,255,255,0.55)",
                                opacity: 1,
                            },
                        }}
                    />

                    <Button
                        type="submit"
                        variant="contained"
                        disabled={!props.searchQuery.trim()}
                        sx={{
                            ...pillBtnSx,
                            bgcolor: "#9d7dff",
                            "&:hover": { bgcolor: "#7f5aff" },
                            minWidth: 110,
                            py: 0.9,
                        }}
                    >
                        Search
                    </Button>
                </Stack>
            </Box>

            {props.loading && (
                <Stack alignItems="center" py={2}>
                    <CircularProgress sx={{ color: "#fff" }} />
                </Stack>
            )}

            {!!props.results.length && (
                <Stack spacing={1} sx={{ mt: 1.25 }}>
                    {props.results.map((p) => (
                        <Box key={p.playerId} sx={rowSx}>
                            <Stack direction="row" alignItems="center" justifyContent="space-between" spacing={1.5}>
                                <Typography sx={{ color: "#fff", fontWeight: 800 }} noWrap>
                                    {p.username}
                                </Typography>
                                <Button
                                    variant="contained"
                                    size="small"
                                    startIcon={<PersonAddRoundedIcon />}
                                    onClick={() => props.onAdd(p.playerId)}
                                    disabled={props.addPending}
                                    sx={{
                                        ...pillBtnSx,
                                        bgcolor: "#22c55e",
                                        "&:hover": { bgcolor: "#16a34a" },
                                    }}
                                >
                                    Add
                                </Button>
                            </Stack>
                        </Box>
                    ))}
                </Stack>
            )}

            {props.submittedQuery && !props.loading && props.results.length === 0 && (
                <Typography sx={{ color: "rgba(255,255,255,0.72)", mt: 1.25 }}>
                    No players found (or already friends / pending).
                </Typography>
            )}
        </Box>
    );
}
