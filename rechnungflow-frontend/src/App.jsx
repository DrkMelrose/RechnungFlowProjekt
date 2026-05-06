import React from 'react';
import {BrowserRouter, Routes, Route} from "react-router-dom";

import Dashboard from "./pages/Dashboard.jsx";
import Clients from "./pages/Clients.jsx";
import Employee from "./pages/Employee.jsx";
import GenerateInvoice from "./pages/GenerateInvoice.jsx";
import Invoices from "./pages/Invoices.jsx";
import Objects from "./pages/Objects.jsx";
import Settings from "./pages/Settings.jsx";
import WorkLogs from "./pages/WorkLogs.jsx";
import Layout from "./components/Layout.jsx";

export default function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route element={<Layout />}>
              <Route path="/" element={<Dashboard/>} />
              <Route path="/clients" element={<Clients />} />
              <Route path="/employees" element={<Employee/>} />
              <Route path="/generate-invoice" element={<GenerateInvoice/>} />
              <Route path="/invoices" element={<Invoices/>} />
              <Route path="/objects" element={<Objects/>} />
              <Route path="/settings" element={<Settings/>} />
              <Route path="/worklogs" element={<WorkLogs/>} />
          </Route>
        </Routes>
      </BrowserRouter>
  )
}
