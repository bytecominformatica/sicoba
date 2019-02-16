import React from 'react';
import PropTypes from 'prop-types';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import Paper from '@material-ui/core/Paper';
import withStyles from '@material-ui/core/styles/withStyles';
import logo from '../../images/logo.png';
import backgroundLogin from '../../images/bg-login.png';

const styles = theme => ({
    root: {
        height: '100%',
        paddingTop: theme.spacing.unit * 1,
        paddingBottom: theme.spacing.unit * 1,
        [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
            backgroundImage: `url(${backgroundLogin})`,
            backgroundRepeat: 'no-repeat',
            backgroundSize: 'cover',
        },
    },
    main: {
        width: 'auto',
        height: '100%',
        display: 'flex', 
        alignItems: 'center',
        marginLeft: theme.spacing.unit * 1,
        marginRight: theme.spacing.unit * 1,
        [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
            width: 400,
            marginLeft: 'auto',
            marginRight: 'auto',
        },
    },
    paper: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
    },
    logo: {
        width: `${theme.spacing.unit * 20}px`,
        marginTop: theme.spacing.unit * 6,
    },
    form: {
        height: '100%',
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing.unit * 6,
    },
    submit: {
        marginTop: theme.spacing.unit * 3,
    },
});

function LoginPage(props) {
    const { classes } = props;

    return (
        <div className={classes.root}>
            <div className={classes.main}>
                <CssBaseline />
                <Paper className={classes.paper}>
                    <img src={logo} className={classes.logo} alt='logo bytecom' />
                    <form className={classes.form}>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="username">Usuário</InputLabel>
                            <Input id="username" name="username" autoComplete="username" autoFocus />
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel htmlFor="password">Senha</InputLabel>
                            <Input name="password" type="password" id="password" autoComplete="current-password" />
                        </FormControl>
                        <FormControlLabel
                            control={<Checkbox value="remember" color="primary" />}
                            label="Lembrar"
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            className={classes.submit}
                        >
                            Entrar
                    </Button>
                    </form>
                </Paper>
            </div>
        </div>
    );
}

LoginPage.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(LoginPage);