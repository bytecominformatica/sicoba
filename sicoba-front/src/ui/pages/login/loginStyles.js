import backgroundLogin from "../../../images/bg-login.png";

const loginStyles = theme => ({
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

export default loginStyles;