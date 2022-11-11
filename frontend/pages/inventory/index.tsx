import { LoadingButton } from "@mui/lab";
import { Avatar, Box, Button, Divider, Typography } from "@mui/material";
import { NextPage } from "next";
import Head from "next/head";
import { useCallback, useEffect, useState } from "react";
import toast from "react-hot-toast";
import { Modal } from "../../src/components/modal";
import { CreateEditSupplier } from "../../src/components/suppliers/create-edit-supplier";
import { SupplierTable } from "../../src/components/suppliers/supplier-table";
import { TabPanel } from "../../src/components/tab-panel";
import { withMainLayout } from "../../src/hocs/with-main-layout";
import { deleteSupplier, getAllSuppliers } from "../../src/slices/suppliers";
import { useDispatch, useSelector } from "../../src/store";
import { mapAxiosError } from "../../src/util/map-axios-error";
import DeleteIcon from '@mui/icons-material/Delete';
import { getAllProducts } from "../../src/slices/products";
import { getAllRawMaterials, sortItemsByThreshold } from "../../src/slices/raw-materials";
import { RawMaterialTable } from "../../src/components/raw-materials/raw-materials-table";
import { ProductsTable } from "../../src/components/products/products-table";
import { CreateEditRawMaterial } from "../../src/components/raw-materials/create-edit-raw-material";
import { CreateEditProduct } from "../../src/components/products/create-edit-product";
import { withAuthGuard } from "../../src/hocs/with-auth-guard";
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';

type TabType = "raw-material" | "product" | "supplier";

const tabs: { key: TabType; value: string }[] = [
  {
    key: "raw-material",
    value: "Raw Materials",
  },
  {
    key: "product",
    value: "Products",
  },
  {
    key: "supplier",
    value: "Suppliers",
  },
];

export const InventoryPage: NextPage = () => {
  const [selectedTab, setSelectedTab] = useState<TabType>("raw-material");
  const dispatch = useDispatch();
  const { suppliers } = useSelector((state) => state.suppliers);
  const { rawMaterials } = useSelector((state) => state.rawMaterials);
  const { products } = useSelector((state) => state.products);
  const [manageProps, setManageProps] = useState<{ type: TabType, mode: 'new' | 'edit', id?: number } | undefined>(undefined);
  const [deleteId, setDeleteId] = useState<number | undefined>(undefined);
  const [removing, setRemoving] = useState<boolean>(false);

  const handleTabChanged = (newTab: string) => {
    setSelectedTab(newTab as TabType);
  }

  const loadSuppliers = useCallback(async () => {
    try {
      await dispatch(getAllSuppliers());
    } catch (err) {
      toast.error("We ran into an error while loading suppliers. Please try again");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const loadProducts = useCallback(async () => {
    try {
      await dispatch(getAllProducts());
    } catch (err) {
      toast.error("We ran into an error while loading products. Please try again");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const loadRawMaterials = useCallback(async () => {
    try {
      await dispatch(getAllRawMaterials());
    } catch (err) {
      toast.error("We ran into an error while loading the raw materials. Please try again");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);


  useEffect(() => {
    if (selectedTab === 'product') {
      // load products
      loadProducts();
      return;
    }
    if (selectedTab === 'raw-material') {
      // load raw materials
      loadRawMaterials();
      return;
    }
    if (selectedTab === 'supplier') {
      // load suppliers
      loadSuppliers();
    }
  }, [loadProducts, loadRawMaterials, loadSuppliers, selectedTab]);

  const handleEditTableItemClick = useCallback((id: number) => {
    setManageProps({ type: selectedTab, mode: 'edit', id });
  }, [selectedTab]);

  const handleDeleteTableItemClick = useCallback((id: number) => {
    setDeleteId(id);
  }, []);

  const handleNewClick = () => {
    setManageProps({ type: selectedTab, mode: 'new' });
  };

  const handleManageClose = () => {
    setManageProps(undefined);
  }

  const handleSortClick = useCallback(async (direction: 'ascending' | 'descending') => {
    try {
      await dispatch(sortItemsByThreshold(direction));
    } catch (err) {
      toast.error(mapAxiosError(err))
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleDeleteItem = useCallback(async () => {
    try {
      if (!deleteId) {
        toast.error("Select an item to delete");
        return;
      }
      setRemoving(true)
      if (selectedTab === "product") {
        // remove product
      }
      if (selectedTab === "raw-material") {
        // remove raw material
      }
      if (selectedTab === "supplier") {
        // remove supplier
        await dispatch(deleteSupplier(deleteId));
      }
      setDeleteId(undefined);
      toast.success("Item successfully deleted");
    }
    catch (err) {
      toast.error(mapAxiosError(err));
    } finally {
      setRemoving(false);
    }
  }, [deleteId, selectedTab]);

  return (
    <>
      <Head>
        <title>Inventory | SCM</title>
      </Head>
      <Box>
        <Typography variant="h5">
          Inventory Management
        </Typography>
        <Typography variant="body2">
          Manage all raw materials, manufactured products and suppliers in the supply chain.
        </Typography>
        <Divider sx={{ mb: 2, mt: 1 }} />
        <Box sx={{ py: 2, display: 'flex', alignItems: 'center' }}>
          <TabPanel tabs={tabs}
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

        {selectedTab === 'raw-material' && (
          <Box sx={{ my: 2, display: 'flex', gap: 2, alignItems: 'center' }}>
            <Button
              variant="outlined"
              onClick={() => handleSortClick('ascending')}
              endIcon={<ArrowUpwardIcon />}
            >
              Sort by Threshold (Ascending)
            </Button>
            <Button
              variant="outlined"
              onClick={() => handleSortClick('descending')}
              endIcon={<ArrowDownwardIcon />}
            >
              Sort by Threshold (Descending)
            </Button>
          </Box>
        )}

        {selectedTab === 'product' && (
          <ProductsTable
            products={products}
            onEditClick={handleEditTableItemClick}
          />
        )}

        {selectedTab === 'raw-material' && (
          <RawMaterialTable
            rawMaterials={rawMaterials}
            onEditClick={handleEditTableItemClick}
          />
        )}

        {selectedTab === 'supplier' && (<SupplierTable suppliers={suppliers}
          onEditClick={handleEditTableItemClick}
          onDeleteClick={handleDeleteTableItemClick} />)}

      </Box>

      {
        manageProps && manageProps.type === "supplier" && (
          <CreateEditSupplier
            onClose={() => handleManageClose()}
            open
            {...manageProps.id && {
              supplier: suppliers.find((supplier) => supplier.id === manageProps.id)
            }}
          />
        )
      }

      {
        manageProps && manageProps.type === "raw-material" && (
          <CreateEditRawMaterial
            onClose={() => handleManageClose()}
            open
            {...manageProps.id && {
              rawMaterial: rawMaterials.find((material) => material.id === manageProps.id)
            }}
          />
        )
      }

      {
        manageProps && manageProps.type === "product" && (
          <CreateEditProduct
            onClose={() => handleManageClose()}
            open
            {...manageProps.id && {
              product: products.find((product) => product.id === manageProps.id)
            }}
          />
        )
      }

      {
        deleteId && (
          <Modal
            open
            onClose={() => setDeleteId(undefined)}
            title="Remove Entity"
            negativeAction={<Button variant="outlined" color="error"
              onClick={() => setDeleteId(undefined)}
            >Close</Button>}
            positiveAction={<LoadingButton
              variant="contained"
              color="primary"
              loading={removing}
              onClick={() => handleDeleteItem()}
            >
              Delete
            </LoadingButton>}>
            <Box sx={{ display: 'flex', gap: 2 }}>
              <Box>
                <Avatar
                  sx={{
                    background: (theme) => theme.palette.error.main
                  }}
                >
                  <DeleteIcon />
                </Avatar>
              </Box>
              <Box>
                <Typography variant="body2">
                  Are you sure you want to perform this delete?
                </Typography>
                <Typography variant="subtitle2">
                  This action is irreversible.
                </Typography>
              </Box>
            </Box>
          </Modal>
        )
      }
    </>
  );
};

export default withAuthGuard(withMainLayout(InventoryPage));
