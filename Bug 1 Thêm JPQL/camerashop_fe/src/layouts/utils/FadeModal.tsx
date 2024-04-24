import * as React from "react";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import Fade from "@mui/material/Fade";
import {Slide} from "@mui/material";

interface FadeModalProps {
    open: boolean;
    handleClose: () => void;
    children: React.ReactNode;
    className: string; // Add className prop
}

const FadeModal: React.FC<FadeModalProps> = ({open, handleClose, children}) => {
    const modalStyle = {
        position: "absolute" as "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)",
        width: "80%",
        maxHeight: "600px",
        overflowY: "scroll",
        bgcolor: "background.paper",
        borderRadius: 3,
        boxShadow: 24,
        p: 4,
    };

    return (
        <Modal
            open={open}
            onClose={handleClose}
            closeAfterTransition
            BackdropProps={{
                timeout: 500,
            }}
        >
            <Fade in={open}>
                <Box sx={modalStyle}>{children}</Box>
            </Fade>
        </Modal>
    );
};

export default FadeModal;
