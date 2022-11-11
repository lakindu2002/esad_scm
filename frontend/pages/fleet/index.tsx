import { Box, Button, Divider, Typography } from "@mui/material";
import { NextPage } from "next";
import Head from "next/head";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { TabPanel } from "../../src/components/tab-panel";
import { CreateEditVehicle } from "../../src/components/vehicles/create-edit-vehicle";
import { VehicleTable } from "../../src/components/vehicles/vehicle-table";
import { withAuthGuard } from "../../src/hocs/with-auth-guard";
import { withMainLayout } from "../../src/hocs/with-main-layout";
import { getVehicles } from "../../src/slices/vehicles";
import { useDispatch, useSelector } from "../../src/store";
import { VehicleType } from "../../src/types/vehicle";
import { mapAxiosError } from "../../src/util/map-axios-error";


const tabs: { value: string, key: VehicleType }[] = [
  {
    value: 'Cars',
    key: 'car'
  },
  {
    value: 'Trucks',
    key: 'truck'
  },
  {
    value: 'Vans',
    key: 'van'
  }
]

export const FleetPage: NextPage = () => {
  const [selectedTab, setSelectedTab] = useState<VehicleType>("car");
  const { vehicles } = useSelector((state) => state.vehicles);
  const dispatch = useDispatch();
  const [manageProps, setManageProps] = useState<{ mode: 'new' | 'edit', id?: number } | undefined>(undefined);

  const handleTabChanged = (newTab: string) => {
    setSelectedTab(newTab as VehicleType);
  }

  const handleNewClick = () => {
    setManageProps({ mode: 'new' });
  }

  useEffect(() => {
    const loadVehiclesPerType = async (type: VehicleType) => {
      try {
        await dispatch(getVehicles(type));
      } catch (err) {
        toast.error(mapAxiosError(err));
      }
    }
    loadVehiclesPerType(selectedTab);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedTab]);

  const handleEditClick = (id: number) => {
    setManageProps({ mode: 'edit', id });
  };

  const handleManageClose = () => {
    setManageProps(undefined);
  }

  return (
    <>
      <Head>
        <title>Fleet | SCM</title>
      </Head>
      <Box>
        <Typography variant="h5">Fleet Management</Typography>
        <Typography variant="body2">
          Manage all vehicles in your fleet to aid in order delivery.
        </Typography>
      </Box>
      <Divider sx={{ mb: 2, mt: 1 }} />
      <Box sx={{ py: 2, display: 'flex', alignItems: 'center' }}>
        <TabPanel
          tabs={tabs}
          onTabChanged={handleTabChanged}
          selectedTab={selectedTab}
        />
        <Button sx={{ ml: 'auto', width: 100 }}
          onClick={handleNewClick}
          variant="contained"
        >
          New
        </Button>
      </Box>
      <VehicleTable
        vehicles={vehicles}
        onEditClick={handleEditClick}
      />
      {manageProps && (
        <CreateEditVehicle
          type={selectedTab}
          onClose={() => handleManageClose()}
          open
          {...manageProps.id && {
            vehicle: vehicles.find((vehicle) => vehicle.id === manageProps.id)
          }} />
      )}
    </>
  );
};

export default withAuthGuard(withMainLayout(FleetPage));
