import { Box, Dialog, DialogActions, DialogContent, DialogTitle, IconButton, Typography } from "@mui/material";
import { FC } from "react";
import CloseIcon from "@mui/icons-material/Close";

interface ModalProps {
    title: string
    open: boolean;
    onClose: () => void;
    children: React.ReactNode;
    positiveAction: React.ReactNode
    negativeAction: React.ReactNode
}

export const Modal: FC<ModalProps> = (props) => {
    const { title, onClose, open, children, negativeAction, positiveAction } = props;
    return (
        <Dialog
            open={open}
            onClose={onClose}
            maxWidth="md"
        >
            <DialogTitle>
                <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                    <Typography variant="h6">
                        {title}
                    </Typography>
                    <IconButton onClick={() => onClose()}>
                        <CloseIcon />
                    </IconButton>
                </Box>
            </DialogTitle>
            <DialogContent>
                {children}
            </DialogContent>
            <DialogActions>
                {negativeAction}
                {positiveAction}
            </DialogActions>
        </Dialog>
    )
}