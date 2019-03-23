import React from 'react';
import PropTypes from 'prop-types';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Paper from '@material-ui/core/Paper';
import withStyles from '@material-ui/core/styles/withStyles';
import logo from '../../../images/logo.png';
import {IconButton, InputAdornment, Snackbar} from "@material-ui/core";
import {AuthContext} from "../../../contexts/auth-context";
import loginStyles from "./loginStyles";
import MyTextField from "../../components/inputs/MyTextField";
import CloseIcon from '@material-ui/icons/Close';
import MySnackbar from "../../components/MySnackbar";

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

    login = async (event) => {
        event.preventDefault();
        let {username, password, lembrar} = this.state;
        await this.context.login(username, password, lembrar);
        // console.log('response', response);
        // let credential = {
        //     ...this.state,
        //     nome: 'Clairton Luz',
        //     iniciais: 'CL',
        //     email: 'clairton.luz@bytecominformatica.com.br'
        // };
        // this.context.login(credential);
    };

    render() {
        let {classes} = this.props;
        let currentUserSnapshot = this.context.currentUser;
        console.log('user', currentUserSnapshot);
        // return <Test/>;
        return (
            <div className={classes.root}>
                <div className={classes.main}>
                    <CssBaseline/>
                    <Paper className={classes.paper}>
                        <img src={logo} className={classes.logo} alt='logo bytecom'/>

                        <form className={classes.form} onSubmit={this.login}>
                            <FormControl margin="normal" required fullWidth>
                                <MyTextField
                                    id="username"
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
                                <MyTextField
                                    id="password"
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

                        {/*<MySnackbar message={'testse'}*/}
                                    {/*// onClose={this.context.logout}*/}
                            {/*onClose={()=> console.log('close')}*/}
                                    {/*// open={currentUserSnapshot.hasError()}*/}
                                    {/*open={true}*/}
                        {/*/>*/}
                        <Snackbar
                            anchorOrigin={{
                                vertical: 'bottom',
                                horizontal: 'left',
                            }}
                            open={currentUserSnapshot.hasError()}
                            autoHideDuration={6000}
                            onClose={this.context.logout}
                            ContentProps={{
                                'aria-describedby': 'message-id',
                            }}
                            message={<span id="message-id">{currentUserSnapshot.error}</span>}
                            action={[
                                <Button key="undo" color="secondary" size="small" onClick={this.context.logout}>
                                    UNDO
                                </Button>,
                                <IconButton
                                    key="close"
                                    aria-label="Close"
                                    color="inherit"
                                    className={classes.close}
                                    onClick={this.context.logout}
                                >
                                    <CloseIcon />
                                </IconButton>,
                            ]}
                        />
                    </Paper>
                </div>
            </div>
        );
    }
}

LoginPage.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(loginStyles)(LoginPage);