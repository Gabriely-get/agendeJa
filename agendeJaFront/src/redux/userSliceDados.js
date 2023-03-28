import { createSlice } from "@reduxjs/toolkit";

export const slice = createSlice({
  name: "userDados",
  initialState: {
    id: "",
    email: "",
    password: "",
    username: "",
    birthday: "",
    cpf: "",
    phone: "",
    isActive: "",
    createAt: "",
    updateAt: "",
    roles: "",
  },
  reducers: {
    addInfoUser(state, { payload }) {
      return {
        ...state,
        id: payload.data.id,
        email: payload.data.email,
        password: payload.data.password,
        username: payload.data.username,
        birthday: payload.data.birthday,
        cpf: payload.data.cpf,
        phone: payload.data.phone,
        isActive: payload.data.isActive,
        createAt: payload.data.createAt,
        updateAt: payload.data.updateAt,
        roles: payload.data.roles,
      };
    },
  },
});

export const { addInfoUser } = slice.actions;

export const selectUser = (state) => state.userDados;

export default slice.reducer;
