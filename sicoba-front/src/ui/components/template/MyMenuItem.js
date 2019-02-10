import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import { withRouter } from "react-router-dom";

const styles = theme => ({
    root: {
        width: '100%',
        maxWidth: 360,
        backgroundColor: theme.palette.background.paper,
    },
});

class MyMenuItem extends React.Component {

    handleClick = () => {
        const { afterClicked, history, url, location } = this.props;
        if (location.pathname !== url) history.push(url);
        if (afterClicked) afterClicked();
    };

    render() {
        const { label, icon, className } = this.props;
        return (
            <ListItem button onClick={this.handleClick} className={className}>
                {icon && (
                    <ListItemIcon>
                        {icon}
                    </ListItemIcon>
                )}
                <ListItemText inset primary={label} />
            </ListItem>
        )
    }
}
MyMenuItem.propTypes = {
    classes: PropTypes.object.isRequired,
    className: PropTypes.string,
    afterClicked: PropTypes.func,
    label: PropTypes.string.isRequired,
    url: PropTypes.string.isRequired,
    icon: PropTypes.element,
};

export default withStyles(styles)(
    withRouter(MyMenuItem)
);