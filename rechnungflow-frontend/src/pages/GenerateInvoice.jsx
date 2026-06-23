import React, { useState } from "react";
import { clients, workLogs } from "../data/mockData.js";
import { FilePlus2, Search, CheckCircle, Clock } from "lucide-react";

export default function GenerateInvoice() {
    const [selectedClient, setSelectedClient] = useState("");
    const [searchTerm, setSearchTerm] = useState("");

    const approvedLogs = workLogs.filter((log) => log.status === "Approved");

    const filteredLogs = approvedLogs.filter((log) => {
        const matchesClient = selectedClient === "" || log.client === selectedClient;

        const matchesSearch =
            log.employee.toLowerCase().includes(searchTerm.toLowerCase()) ||
            log.object.toLowerCase().includes(searchTerm.toLowerCase()) ||
            log.client.toLowerCase().includes(searchTerm.toLowerCase());

        return matchesClient && matchesSearch;
    });

    const totalHours = filteredLogs.reduce((sum, log) => sum + log.hours, 0);
    const hourlyRate = 35;
    const estimatedAmount = totalHours * hourlyRate;

    const formatCurrency = (value) =>
        new Intl.NumberFormat("de-DE", {
            style: "currency",
            currency: "EUR",
        }).format(value);

    return (
        <div className="space-y-6">
            {/* Header */}
            <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
                <div>
                    <h1 className="text-2xl font-semibold text-slate-900">
                        Generate Invoice
                    </h1>
                    <p className="text-slate-500">
                        Create invoices from approved work logs.
                    </p>
                </div>

                <button className="flex items-center justify-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-xl text-sm font-medium hover:bg-blue-700 transition">
                    <FilePlus2 size={16} />
                    Generate Invoice
                </button>
            </div>

            {/* Filters */}
            <div className="bg-white border border-slate-200 rounded-2xl p-4 grid gap-4 md:grid-cols-3">
                <div>
                    <label className="block text-sm font-medium text-slate-700 mb-2">
                        Client
                    </label>

                    <select
                        value={selectedClient}
                        onChange={(e) => setSelectedClient(e.target.value)}
                        className="w-full px-4 py-2 border border-slate-200 rounded-xl text-sm outline-none focus:ring-2 focus:ring-blue-100 focus:border-blue-400"
                    >
                        <option value="">All clients</option>
                        {clients.map((client) => (
                            <option key={client.id} value={client.name}>
                                {client.name}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="md:col-span-2">
                    <label className="block text-sm font-medium text-slate-700 mb-2">
                        Search
                    </label>

                    <div className="relative">
                        <Search
                            size={18}
                            className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"
                        />

                        <input
                            type="text"
                            placeholder="Search by employee, object or client..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="w-full pl-10 pr-4 py-2 border border-slate-200 rounded-xl text-sm outline-none focus:ring-2 focus:ring-blue-100 focus:border-blue-400"
                        />
                    </div>
                </div>
            </div>

            {/* Summary Cards */}
            <div className="grid gap-4 md:grid-cols-3">
                <SummaryCard
                    title="Approved Logs"
                    value={filteredLogs.length}
                    subtitle="ready for invoicing"
                />

                <SummaryCard
                    title="Total Hours"
                    value={`${totalHours.toFixed(1)} h`}
                    subtitle="approved working time"
                />

                <SummaryCard
                    title="Estimated Amount"
                    value={formatCurrency(estimatedAmount)}
                    subtitle={`based on €${hourlyRate}/h`}
                />
            </div>

            {/* Desktop Table */}
            <div className="hidden md:block bg-white border border-slate-200 rounded-2xl overflow-hidden shadow-sm">
                {filteredLogs.length > 0 ? (
                    <table className="w-full text-sm">
                        <thead className="bg-slate-50 text-slate-500">
                        <tr>
                            <th className="text-left px-6 py-4 font-medium">Date</th>
                            <th className="text-left px-6 py-4 font-medium">Employee</th>
                            <th className="text-left px-6 py-4 font-medium">Object</th>
                            <th className="text-left px-6 py-4 font-medium">Client</th>
                            <th className="text-left px-6 py-4 font-medium">Hours</th>
                            <th className="text-left px-6 py-4 font-medium">Status</th>
                        </tr>
                        </thead>

                        <tbody>
                        {filteredLogs.map((log) => (
                            <tr
                                key={log.id}
                                className="border-t border-slate-100 hover:bg-slate-50 transition"
                            >
                                <td className="px-6 py-4 text-slate-600">{log.date}</td>

                                <td className="px-6 py-4 font-semibold text-slate-900">
                                    {log.employee}
                                </td>

                                <td className="px-6 py-4 text-slate-600">{log.object}</td>

                                <td className="px-6 py-4 text-slate-600">{log.client}</td>

                                <td className="px-6 py-4 font-semibold text-slate-900">
                                    {log.hours.toFixed(1)} h
                                </td>

                                <td className="px-6 py-4">
                    <span className="inline-flex items-center gap-1 px-3 py-1 rounded-full text-xs font-medium bg-green-50 text-green-700">
                      <CheckCircle size={14} />
                      Approved
                    </span>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                ) : (
                    <EmptyState />
                )}
            </div>

            {/* Mobile Cards */}
            <div className="md:hidden space-y-4">
                {filteredLogs.length > 0 ? (
                    filteredLogs.map((log) => (
                        <div
                            key={log.id}
                            className="bg-white border border-slate-200 rounded-2xl p-4 shadow-sm"
                        >
                            <div className="flex items-start gap-3">
                                <div className="w-10 h-10 rounded-xl bg-blue-50 text-blue-600 flex items-center justify-center shrink-0">
                                    <Clock size={20} />
                                </div>

                                <div className="min-w-0 flex-1">
                                    <h3 className="font-semibold text-slate-900 truncate">
                                        {log.object}
                                    </h3>
                                    <p className="text-sm text-slate-500 truncate">
                                        {log.employee}
                                    </p>
                                </div>

                                <span className="shrink-0 inline-flex items-center gap-1 px-2 py-1 rounded-full text-xs font-medium bg-green-50 text-green-700">
                  <CheckCircle size={14} />
                  Approved
                </span>
                            </div>

                            <div className="mt-4 grid grid-cols-2 gap-3 text-sm">
                                <div>
                                    <p className="text-slate-400">Date</p>
                                    <p className="text-slate-700">{log.date}</p>
                                </div>

                                <div>
                                    <p className="text-slate-400">Hours</p>
                                    <p className="font-semibold text-slate-900">
                                        {log.hours.toFixed(1)} h
                                    </p>
                                </div>

                                <div className="col-span-2">
                                    <p className="text-slate-400">Client</p>
                                    <p className="text-slate-700 truncate">{log.client}</p>
                                </div>
                            </div>
                        </div>
                    ))
                ) : (
                    <EmptyState />
                )}
            </div>
        </div>
    );
}

function SummaryCard({ title, value, subtitle }) {
    return (
        <div className="bg-white border border-slate-200 rounded-2xl p-5 shadow-sm">
            <p className="text-sm text-slate-500">{title}</p>
            <p className="mt-2 text-2xl font-semibold text-slate-900">{value}</p>
            <p className="mt-1 text-sm text-slate-400">{subtitle}</p>
        </div>
    );
}

function EmptyState() {
    return (
        <div className="py-16 text-center">
            <p className="text-slate-900 font-medium">No approved work logs found</p>
            <p className="text-sm text-slate-500 mt-1">
                Only approved work logs can be used for invoice generation.
            </p>
        </div>
    );
}