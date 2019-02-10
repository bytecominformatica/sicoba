import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
import { Collapse } from '@material-ui/core';

const styles = theme => ({
    root: {
        width: '100%',
        maxWidth: 360,
        backgroundColor: theme.palette.background.paper,
    },
});

class MyMenuItem extends React.Component {
    state = {
        open: false,
    };

    handleClick = () => {
        this.setState(state => ({ open: !state.open }));
    };

    render() {
        const { label, icon, children, className } = this.props;
        return (
            <>
                <ListItem button onClick={this.handleClick} className={className}>
                    {icon && (
                        <ListItemIcon>
                            {icon}
                        </ListItemIcon>
                    )}
                    <ListItemText inset primary={label} />
                    {this.state.open ? <ExpandLess /> : <ExpandMore />}
                </ListItem>
                <Collapse in={this.state.open} timeout="auto" unmountOnExit>
                    <List component="div" disablePadding>
                        {children}
                    </List>
                </Collapse>

            </>
        )
    }
}
MyMenuItem.propTypes = {
    classes: PropTypes.object.isRequired,
    className: PropTypes.string,
    afterClicked: PropTypes.func,
    label: PropTypes.string.isRequired,
    icon: PropTypes.element,
};

export default withStyles(styles)(MyMenuItem);