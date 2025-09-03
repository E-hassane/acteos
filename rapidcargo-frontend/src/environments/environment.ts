declare const process: any;

export const environment = {
  production: false,
  apiUrl: process.env['API_URL'] || 'http://localhost:8080/api',
  appName: process.env['APP_NAME'] || 'RapidCargo Frontend'
};
