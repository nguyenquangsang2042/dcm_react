import { createStore, combineReducers } from 'redux';
import authReducer from '../reducers/authReducer';
import { loadingReducer, loginReducer } from "../reducers/LoginReducer";

const rootReducer = combineReducers({
  auth: authReducer,
  login: loginReducer,
  loading: loadingReducer,
});

const store = createStore(rootReducer);

export default store;
