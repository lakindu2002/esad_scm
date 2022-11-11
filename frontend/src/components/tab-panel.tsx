import { Box, Tab, Tabs } from "@mui/material";
import { FC, useCallback, useEffect, useState } from "react";

interface TabPanelProps {
  tabs: { key: string; value: string }[];
  onTabChanged: (key: string) => void;
  selectedTab: string;
}

export const TabPanel: FC<TabPanelProps> = (props) => {
  const { onTabChanged, tabs, selectedTab: propSelectedTab } = props;
  const [selectedTab, setSelectedTab] = useState<string>(propSelectedTab);

  useEffect(() => {
    setSelectedTab(propSelectedTab);
  }, [propSelectedTab]);

  const handleTabChange = useCallback(
    (value: string) => {
      setSelectedTab(value);
      onTabChanged(value);
    },
    [onTabChanged]
  );
  return (
    <>
      <Box sx={{ width: "100%" }}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <Tabs
            value={selectedTab}
            onChange={(_e, index) => handleTabChange(index)}
          >
            {tabs.map((tab) => (
              <Tab
                key={tab.key}
                label={tab.value}
                value={tab.key}
              />
            ))}
          </Tabs>
        </Box>
      </Box>
    </>
  );
};
