import React, { useState } from "react";
import { clients } from "../data/mockData.js";
import { Plus, Search, Pencil, Trash2 } from "lucide-react";

export default function Clients() {
    const [searchTerm, setSearchTerm] = useState("");

    const filteredClients = clients.filter((client) =>
        client.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div className="space-y-6">
            {/* Header */}
            <div className="flex items-center justify-between">
                <div>
                    <h1 className="text-2xl font-semibold text-slate-900">Clients</h1>
                    <p className="text-slate-500">
                        Manage your customers and their cleaning contracts.
                    </p>
                </div>

                <button className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-xl text-sm font-medium hover:bg-blue-700 transition">
                    <Plus size={16} />
                    Add Client
                </button>
            </div>

            {/* Toolbar */}
            <div className="bg-white border border-slate-200 rounded-2xl p-4 flex items-center justify-between">
                <div className="relative w-full max-w-sm">
                    <Search
                        size={18}
                        className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"
                    />

                    <input
                        type="text"
                        placeholder="Search clients..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        className="w-full pl-10 pr-4 py-2 border border-slate-200 rounded-xl text-sm outline-none focus:ring-2 focus:ring-blue-100 focus:border-blue-400"
                    />
                </div>

                <p className="text-sm text-slate-500">
                    {filteredClients.length} clients found
                </p>
            </div>

            {/* Table */}
            <div className="bg-white border border-slate-200 rounded-2xl overflow-hidden shadow-sm">
                {filteredClients.length > 0 ? (
                    <table className="w-full text-sm">
                        <thead className="bg-slate-50 text-slate-500">
                        <tr>
                            <th className="text-left px-6 py-4 font-medium">Client</th>
                            <th className="text-left px-6 py-4 font-medium">Email</th>
                            <th className="text-left px-6 py-4 font-medium">Phone</th>
                            <th className="text-left px-6 py-4 font-medium">Address</th>
                            <th className="text-left px-6 py-4 font-medium">Status</th>
                            <th className="text-right px-6 py-4 font-medium">Actions</th>
                        </tr>
                        </thead>

                        <tbody>
                        {filteredClients.map((client) => (
                            <tr
                                key={client.id}
                                className="border-t border-slate-100 hover:bg-slate-50 transition"
                            >
                                <td className="px-6 py-4">
                                    <div>
                                        <p className="font-semibold text-slate-900">
                                            {client.name}
                                        </p>
                                        <p className="text-xs text-slate-500">
                                            Client ID: #{client.id}
                                        </p>
                                    </div>
                                </td>

                                <td className="px-6 py-4 text-slate-600">{client.email}</td>
                                <td className="px-6 py-4 text-slate-600">{client.phone}</td>
                                <td className="px-6 py-4 text-slate-600">{client.address}</td>

                                <td className="px-6 py-4">
                    <span className="inline-flex items-center px-3 py-1 rounded-full text-xs font-medium bg-green-50 text-green-700">
                      Active
                    </span>
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
                    <div className="py-16 text-center">
                        <p className="text-slate-900 font-medium">No clients found</p>
                        <p className="text-sm text-slate-500 mt-1">
                            Try changing your search term.
                        </p>
                    </div>
                )}
            </div>
        </div>
    );
}