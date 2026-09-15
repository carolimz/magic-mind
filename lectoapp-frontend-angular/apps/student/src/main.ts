import { initFederation } from '@angular-architects/native-federation';

initFederation({ 'student': './remoteEntry.json' })
  .catch(err => console.error(err))
  .then(_ => import('./bootstrap'))
  .catch(err => console.error(err));
