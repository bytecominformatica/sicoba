import React from "react";
import PropTypes from 'prop-types'
import Button from "../pages/login/LoginPage";
import {IconButton, Snackbar} from "@material-ui/core";
import CloseIcon from '@material-ui/icons/Close';

// const styles = theme => ({
//     close: {
//         padding: theme.spacing.unit / 2,
//     },
// });

const MySnackbar = ({
                        open,
                        message,
                        autoHideDuration,
                        onClose,
                        classes,
                    }) => (
    <Snackbar
        anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
        }}
        open={open}
        autoHideDuration={autoHideDuration}
        onClose={onClose}
        ContentProps={{
            'aria-describedby': 'message-id',
        }}
        message={<span id="message-id">{message}</span>}
        action={[
            <Button key="undo" color="secondary" size="small" onClick={onClose}>
                UNDO
            </Button>,
            <IconButton
                key="close"
                aria-label="Close"
                color="inherit"
                // className={classes.close}
                onClick={onClose}
            >
                <CloseIcon/>
            </IconButton>,
        ]}
    />
);

MySnackbar.defaultProps = {
    autoHideDuration: 6000,
};

MySnackbar.propTypes = {
    // classes: PropTypes.object.isRequired,
    message: PropTypes.any,
    open: PropTypes.bool.isRequired,
    autoHideDuration: PropTypes.number,
    onClose: PropTypes.func.isRequired,
};

// export default withStyles(styles)(MySnackbar);
export default MySnackbar;