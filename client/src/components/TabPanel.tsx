import { Box, Typography } from "@material-ui/core";
import React from "react";

interface Props {
    children: React.ReactNode,
    index: any,
    value: any,
}

const TabPanel: React.FC<Props> = ({ children, value, index, ...other }) => {
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box p={3}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

export default TabPanel