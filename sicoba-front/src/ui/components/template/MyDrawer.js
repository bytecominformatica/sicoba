import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import {
  AccountCircle, NetworkCheck, Dashboard, Group, TrendingUp,
  AccountBalance, MonetizationOn, Person, AccountBalanceOutlined,
  CloudUpload, Store
} from '@material-ui/icons'
import MyMenuItem from './MyMenuItem';
import MyMenuItemExpandable from './MyMenuItemExpandable';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Typography from '@material-ui/core/Typography';
import menuBackgroundImage from '../../../images/menu-background.jpg';
import { Avatar } from '@material-ui/core';
import { SECONDARY_COLOR } from './colors';

const styles = theme => ({
  list: {
    width: '100%',
    minWidth: 250,
    maxWidth: 300,
    backgroundColor: theme.palette.background.paper,
  },
  nested: {
    paddingLeft: theme.spacing.unit * 4,
  },
  nested2: {
    paddingLeft: theme.spacing.unit * 8,
  },
  card: {
    maxWidth: 300,
    position: 'relative',
  },
  media: {
    // ⚠️ object-fit is not supported by IE 11.
    objectFit: 'cover',
  },
  accountInfo: {
    position: 'absolute',
    top: '20px',
    left: '0px',
    color: 'white'
  },
  avatar: {
    backgroundColor: SECONDARY_COLOR.main,
    marginBottom: '30px',
    width: 60,
    height: 60,
  },
});

class MyDrawer extends React.Component {

  render() {
    const { classes, open, toggleDrawer } = this.props;

    const sideList = (
      <div className={classes.list}>
        <Card className={classes.card}>
          <CardActionArea>
            <CardContent className={classes.accountInfo}>
              <Avatar aria-label="User" className={classes.avatar}>
                C
            </Avatar>
              <Typography gutterBottom variant="h5" component="h2" color='inherit'>
                Clairton Luz
              </Typography>
              <Typography component="p" color='inherit'>
                clairton.c.l@gmail.com.br
              </Typography>
            </CardContent>
            <CardMedia
              component="img"
              alt="Background menu"
              className={classes.media}
              height="200"
              image={menuBackgroundImage}
              title="Background menu"
            />
          </CardActionArea>
        </Card>
        <MyMenuItem label='Dashboad' url='/' icon={<Dashboard />} afterClicked={toggleDrawer(false)} />
        <MyMenuItemExpandable label='Comercial' icon={<i className='fas fa-hand-holding-usd' />}>
          <MyMenuItem url='/clientes' label='Clientes' icon={<AccountCircle />} afterClicked={toggleDrawer(false)} className={classes.nested} />
          <MyMenuItem url='/planos' label='Planos' icon={<NetworkCheck />} afterClicked={toggleDrawer(false)} className={classes.nested} />
        </MyMenuItemExpandable>
        <MyMenuItemExpandable label='Financeiro' icon={<AccountBalance />}>
          <MyMenuItem url='/notas' label='Notas' icon={<MonetizationOn />} afterClicked={toggleDrawer(false)} className={classes.nested} />
          <MyMenuItemExpandable label='Caixa Economica Federal' icon={<AccountBalanceOutlined />} className={classes.nested}>
            <MyMenuItem url='/cef/enviar_retorno' label='Enviar Retorno' icon={<CloudUpload />} afterClicked={toggleDrawer(false)} className={classes.nested2} />
            <MyMenuItem url='/cef/cedentes' label='Cedentes' icon={<Person />} afterClicked={toggleDrawer(false)} className={classes.nested2} />
            <MyMenuItem url='/cef/titulos/report' label='Relatório de títulos' icon={<TrendingUp />} afterClicked={toggleDrawer(false)} className={classes.nested2} />
          </MyMenuItemExpandable>
          <MyMenuItemExpandable label='Gerencianet' icon={<Group />} className={classes.nested}>
            <MyMenuItem url='/gn/contas' label='Contas' icon={<AccountCircle />} afterClicked={toggleDrawer(false)} className={classes.nested2} />
            <MyMenuItem url='/gn/cobrancas/report' label='Relatório de cobranças' icon={<TrendingUp />} afterClicked={toggleDrawer(false)} className={classes.nested2} />
          </MyMenuItemExpandable>
        </MyMenuItemExpandable>
        <MyMenuItemExpandable label='Estoque' icon={<Store />}>
          <MyMenuItem url='/estoque/equipamentos' label='Equipamentos' icon={<i className="fas fa-tools" />} afterClicked={toggleDrawer(false)} className={classes.nested} />
        </MyMenuItemExpandable>
        <MyMenuItemExpandable label='Provedor' icon={<i className='fas fa-server' />}>
          <MyMenuItem url='/provedor/mikrotik' label='Mikrotik' icon={<i className="fas fa-shield-alt" />} afterClicked={toggleDrawer(false)} className={classes.nested} />
        </MyMenuItemExpandable>
      </div>
    );

    return (
      <div>
        <Drawer open={open} onClose={toggleDrawer(false)}>
          <div
            tabIndex={0}
            role="button"
          >
            {sideList}
          </div>
        </Drawer>
      </div>
    );
  }
}

MyDrawer.propTypes = {
  classes: PropTypes.object.isRequired,
  toggleDrawer: PropTypes.func.isRequired,
  open: PropTypes.bool.isRequired,
};

export default withStyles(styles)(MyDrawer);
