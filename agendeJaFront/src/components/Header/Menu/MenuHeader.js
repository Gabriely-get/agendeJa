import { RiArrowDownSFill, RiArrowUpSFill } from "react-icons/ri";
import { Menu, MenuButton, MenuList, MenuItem } from "@chakra-ui/react";
import "./MenuHeader.scss";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import { logout } from "../../../redux/userSlice";
import { removeUser } from "../../../redux/userSliceDados";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";

export default function MenuHeader() {
  const dispatch = useDispatch();
  const userData = useSelector((state) => state?.userDados?.role);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    if (userData === "ADMIN") {
      setIsAdmin(true);
    }
  }, [userData]);

  return (
    <Menu>
      {({ isOpen }) => (
        <>
          <MenuButton _active={isOpen}>
            {isOpen ? <RiArrowUpSFill /> : <RiArrowDownSFill />}
          </MenuButton>
          <MenuList>
            <MenuItem>
              <Link to="/minhaconta" className="anchor">
                Minha conta
              </Link>
            </MenuItem>
            <MenuItem
              onClick={() => {
                dispatch(logout());
                dispatch(removeUser());
              }}
            >
              Sair
            </MenuItem>
            {isAdmin ? (
              <MenuItem>
                <Link to="/administrador" className="anchor">
                  Administrador
                </Link>
              </MenuItem>
            ) : (
              ""
            )}
          </MenuList>
        </>
      )}
    </Menu>
  );
}
