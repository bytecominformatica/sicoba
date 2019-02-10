import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import MyDrawer from './MyDrawer';

const styles = {
    root: {
        flexGrow: 1,
    },
    grow: {
        flexGrow: 1,
    },
    menuButton: {
        marginLeft: -12,
        marginRight: 20,
    },
};

class MyAppBar extends React.Component {
    state = {
        open: true,
    };

    toggleDrawer = (open) => () => {
        this.setState({
            open: open,
        });
    };

    render() {
        const { classes } = this.props;
        return (
            <Fragment>
                <MyDrawer open={this.state.open} toggleDrawer={this.toggleDrawer} />
                <div className={classes.root}>
                    <AppBar position="static">
                        <Toolbar>
                            <IconButton className={classes.menuButton} color="inherit"
                                aria-label="Menu" onClick={this.toggleDrawer(true)}>
                                <MenuIcon />
                            </IconButton>
                            <Typography variant="h6" color="inherit" className={classes.grow}>
                                SICOBA - Bytecom
                             </Typography>
                            <Button color="inherit">Sair</Button>
                        </Toolbar>
                    </AppBar>
                </div>
            </Fragment>
        );
    }
}

MyAppBar.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(MyAppBar);
