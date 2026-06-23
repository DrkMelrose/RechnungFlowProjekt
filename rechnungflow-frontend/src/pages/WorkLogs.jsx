import React, { useState } from "react";
import { workLogs } from "../data/mockData.js";
import {
    Plus,
    Search,
    Pencil,
    Trash2,
    Clock,
    CheckCircle,
    AlertCircle,
    FileCheck,
} from "lucide-react";

export default function WorkLogs() {
    const [searchTerm, setSearchTerm] = useState("");
    const [statusFilter, setStatusFilter] = useState("All");

    const filteredWorkLogs = workLogs.filter((log) => {
        const matchesSearch =
            log.employee.toLowerCase().includes(searchTerm.toLowerCase()) ||
            log.object.toLowerCase().includes(searchTerm.toLowerCase()) ||
            log.client.toLowerCase().includes(searchTerm.toLowerCase());

        const matchesStatus =
            statusFilter === "All" || log.status === statusFilter;

        return matchesSearch && matchesStatus;
    });

    return (
        <div className="space-y-6">
            {/* Header */}
            <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
                <div>
                    <h1 className="text-2xl font-semibold text-slate-900">Work Logs</h1>
                    <p className="text-slate-500">
                        Track cleaning work, working hours and invoice status.
                    </p>
                </div>

                <button className="flex items-center justify-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-xl text-sm font-medium hover:bg-blue-700 transition">
                    <Plus size={16} />
                    Add Work Log
                </button>
            </div>

            {/* Toolbar */}
            <div className="bg-white border border-slate-200 rounded-2xl p-4 flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
                <div className="relative w-full max-w-sm">
                    <Search
                        size={18}
                        className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"
                    />

                    <input
                        type="text"
                        placeholder="Search work logs..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        className="w-full pl-10 pr-4 py-2 border border-slate-200 rounded-xl text-sm outline-none focus:ring-2 focus:ring-blue-100 focus:border-blue-400"
                    />
                </div>

                <div className="flex flex-col gap-3 sm:flex-row sm:items-center">
                    <select
                        value={statusFilter}
                        onChange={(e) => setStatusFilter(e.target.value)}
                        className="px-4 py-2 border border-slate-200 rounded-xl text-sm outline-none focus:ring-2 focus:ring-blue-100 focus:border-blue-400"
                    >
                        <option value="All">All statuses</option>
                        <option value="Pending">Pending</option>
                        <option value="Approved">Approved</option>
                        <option value="Invoiced">Invoiced</option>
                        <option value="Rejected">Rejected</option>
                    </select>

                    <p className="text-sm text-slate-500">
                        {filteredWorkLogs.length} work logs found
                    </p>
                </div>
            </div>

            {/* Desktop Table */}
            <div className="hidden md:block bg-white border border-slate-200 rounded-2xl overflow-hidden shadow-sm">
                {filteredWorkLogs.length > 0 ? (
                    <table className="w-full text-sm">
                        <thead className="bg-slate-50 text-slate-500">
                        <tr>
                            <th className="text-left px-6 py-4 font-medium">Date</th>
                            <th className="text-left px-6 py-4 font-medium">Employee</th>
                            <th className="text-left px-6 py-4 font-medium">Object</th>
                            <th className="text-left px-6 py-4 font-medium">Client</th>
                            <th className="text-left px-6 py-4 font-medium">Hours</th>
                            <th className="text-left px-6 py-4 font-medium">Status</th>
                            <th className="text-right px-6 py-4 font-medium">Actions</th>
                        </tr>
                        </thead>

                        <tbody>
                        {filteredWorkLogs.map((log) => (
                            <tr
                                key={log.id}
                                className="border-t border-slate-100 hover:bg-slate-50 transition"
                            >
                                <td className="px-6 py-4 text-slate-600">{log.date}</td>

                                <td className="px-6 py-4">
                                    <p className="font-semibold text-slate-900">
                                        {log.employee}
                                    </p>
                                    <p className="text-xs text-slate-500">Log #{log.id}</p>
                                </td>

                                <td className="px-6 py-4 text-slate-600">{log.object}</td>
                                <td className="px-6 py-4 text-slate-600">{log.client}</td>

                                <td className="px-6 py-4 font-semibold text-slate-900">
                                    {log.hours.toFixed(1)} h
                                </td>

                                <td className="px-6 py-4">
                                    <StatusBadge status={log.status} />
                                </td>

                                <td className="px-6 py-4">
                                    <div className="flex justify-end gap-2">
                                        <button className="p-2 rounded-lg text-slate-500 hover:bg-slate-100 hover:text-slate-900">
                                            <Pencil size={16} />
                                        </button>

                                        <button className="p-2 rounded-lg text-slate-500 hover:bg-red-50 hover:text-red-600">
                                            <Trash2 size={16} />
                                        </button>
                                    </div>
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
                {filteredWorkLogs.length > 0 ? (
                    filteredWorkLogs.map((log) => (
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

                                <StatusBadge status={log.status} />
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

                            <div className="mt-4 flex justify-end gap-2">
                                <button className="p-2 rounded-lg text-slate-500 hover:bg-slate-100">
                                    <Pencil size={16} />
                                </button>

                                <button className="p-2 rounded-lg text-slate-500 hover:bg-red-50 hover:text-red-600">
                                    <Trash2 size={16} />
                                </button>
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

function StatusBadge({ status }) {
    const styles = {
        Pending: "bg-yellow-50 text-yellow-700",
        Approved: "bg-green-50 text-green-700",
        Invoiced: "bg-blue-50 text-blue-700",
        Rejected: "bg-red-50 text-red-700",
    };

    const icons = {
        Pending: <AlertCircle size={14} />,
        Approved: <CheckCircle size={14} />,
        Invoiced: <FileCheck size={14} />,
        Rejected: <AlertCircle size={14} />,
    };

    return (
        <span
            className={`inline-flex items-center gap-1 px-3 py-1 rounded-full text-xs font-medium shrink-0 ${
                styles[status] || "bg-slate-100 text-slate-500"
            }`}
        >
      {icons[status]}
            {status}
    </span>
    );
}

function EmptyState() {
    return (
        <div className="py-16 text-center">
            <p className="text-slate-900 font-medium">No work logs found</p>
            <p className="text-sm text-slate-500 mt-1">
                Try changing your search or status filter.
            </p>
        </div>
    );
}