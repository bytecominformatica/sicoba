import React, { Component } from 'react'

import PropTypes from 'prop-types';
import Paper from '@material-ui/core/Paper';
import { withStyles } from '@material-ui/core/styles';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import { Hidden, Tooltip } from '@material-ui/core';

const styles = {
    root: {
        flexGrow: 1,
        // maxWidth: 500,
    },
};

class HomePage extends Component {
    state = {
        value: 0,
    };

    handleChange = (event, value) => {
        this.setState({ value });
    };
    render() {
        const { classes } = this.props;

        return (
            <Paper square className={classes.root}>
                <Tabs
                    value={this.state.value}
                    onChange={this.handleChange}
                    variant="fullWidth"
                    indicatorColor="secondary"
                    textColor="secondary"
                >
                    <Tooltip title='COBRANÇAS EM ATRASO'>
                        <Tab icon={<i class="fas fa-clock fa-2x" />} label={<Hidden xsDown>COBRANÇAS EM ATRASO</Hidden>} />
                    </Tooltip>

                    <Tooltip title='GERAR BOLETOS'>
                        <Tab icon={<i class="fas fa-cash-register fa-2x" />} label={<Hidden xsDown>GERAR BOLETOS</Hidden>} />
                    </Tooltip>

                    <Tooltip title='INSTALAÇÕES RECENTES'>
                        <Tab icon={<i class="fas fa-tools fa-2x" />} label={<Hidden xsDown>INSTALAÇÕES RECENTES</Hidden>} />
                    </Tooltip>

                    <Tooltip title='CLIENTES INATIVOS'>
                        <Tab icon={<i class="fas fa-sad-tear fa-2x" />} label={<Hidden xsDown>CLIENTES INATIVOS</Hidden>} />
                    </Tooltip>

                    <Tooltip title='CLIENTES CANCELADOS'>
                        <Tab icon={<i class="fas fa-ban fa-2x" />} label={<Hidden xsDown>CLIENTES CANCELADOS</Hidden>} />
                    </Tooltip>
                </Tabs>
            </Paper>
        );
    }
}

HomePage.propTypes = {
    classes: PropTypes.object.isRequired,
};


export default withStyles(styles)(HomePage);