import React from 'react';
import PropTypes from 'prop-types';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Paper from '@material-ui/core/Paper';
import withStyles from '@material-ui/core/styles/withStyles';
import logo from '../../images/logo.png';
import backgroundLogin from '../../images/bg-login.png';
import {IconButton, InputAdornment, TextField} from "@material-ui/core";
import {AuthContext} from "../../contexts/auth-context";

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
    textField: {
        // flexBasis: 200,
        // margin: theme.spacing.unit,
    },
});

class LoginPage extends React.Component {
    state = {
        username: '',
        password: '',
        lembrar: false,
        showPassword: false,
    };

    static contextType = AuthContext;

    handleChange = prop => event => {
        this.setState({[prop]: event.target.value});
    };

    handleClickShowPassword = () => {
        this.setState(state => ({showPassword: !state.showPassword}));
    };

    login = (event) => {
        event.preventDefault();
        let credential = {
            ...this.state,
            nome: 'Clairton Luz',
            iniciais: 'CL',
            email: 'clairton.luz@bytecominformatica.com.br'
        };
        this.context.login(credential);
    };

    render() {
        const {classes} = this.props;
        return (
            <div className={classes.root}>
                <div className={classes.main}>
                    <CssBaseline/>
                    <Paper className={classes.paper}>
                        <img src={logo} className={classes.logo} alt='logo bytecom'/>

                        <form className={classes.form} onSubmit={this.login}>
                            <FormControl margin="normal" required fullWidth>
                                <TextField
                                    id="username"
                                    fullWidth
                                    className={classes.textField}
                                    placeholder="Usuário"
                                    value={this.state.username}
                                    onChange={this.handleChange('username')}
                                    autoComplete='username'
                                    autoFocus
                                    InputProps={{
                                        startAdornment: (
                                            <InputAdornment position="start">
                                                <i className='fas fa-user-circle'/>
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                            </FormControl>
                            <FormControl margin="normal" required fullWidth>
                                <TextField
                                    id="password"
                                    fullWidth
                                    className={classes.textField}
                                    type={this.state.showPassword ? 'text' : 'password'}
                                    placeholder="Senha"
                                    value={this.state.password}
                                    onChange={this.handleChange('password')}
                                    autoComplete='current-password'
                                    InputProps={{
                                        startAdornment: (
                                            <InputAdornment position="start">
                                                <i className='fas fa-key'/>
                                            </InputAdornment>
                                        ),
                                        endAdornment: (
                                            <InputAdornment position="end">
                                                <IconButton
                                                    aria-label="Toggle password visibility"
                                                    onClick={this.handleClickShowPassword}
                                                >
                                                    {this.state.showPassword ?
                                                        <i className='fas fa-eye-slash'/> :
                                                        <i className='fas fa-eye'/>}
                                                </IconButton>
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                            </FormControl>

                            <FormControlLabel
                                onChange={this.handleChange('lembrar')}
                                control={<Checkbox value="remember" color="primary"/>}
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
}

LoginPage.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(LoginPage);