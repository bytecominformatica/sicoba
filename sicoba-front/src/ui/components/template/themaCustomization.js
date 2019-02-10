import { createMuiTheme } from '@material-ui/core/styles';
import { SECONDARY_COLOR, PRIMARY_COLOR } from './colors';

const themaCustomization = createMuiTheme({
  palette: {
    primary: PRIMARY_COLOR,
    secondary: SECONDARY_COLOR,
  },
  typography: {
    useNextVariants: true,
  },
});

export default themaCustomization