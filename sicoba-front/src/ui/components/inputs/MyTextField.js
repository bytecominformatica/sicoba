import React from "react";
import PropTypes from 'prop-types';
import {TextField} from "@material-ui/core";
import withStyles from "@material-ui/core/styles/withStyles";


const styles = theme => ({
    textField: {
        // flexBasis: 200,
        // margin: theme.spacing.unit,
    },
});

const MyTextField = ({classes, value, onChange, ...rest}) => (
    <TextField
        className={classes.textField}
        value={value}
        onChange={onChange}
        {...rest}
    />
);

MyTextField.defaultProps = {
    fullWidth: true,
    autoFocus: false,
    type: 'text',
};

MyTextField.propTypes = {
    id: PropTypes.string,
    placeholder: PropTypes.string,
    type: PropTypes.string,
    fullWidth: PropTypes.bool,
    classes: PropTypes.object.isRequired,
    value: PropTypes.any.isRequired,
    onChange: PropTypes.func.isRequired,
    autoComplete: PropTypes.string,
    autoFocus: PropTypes.bool,
    InputProps: PropTypes.object,

};

export default withStyles(styles)(MyTextField);