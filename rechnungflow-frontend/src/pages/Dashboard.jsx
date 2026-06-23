import React from "react";
import KpiCard from '../components/KpiCard.jsx';
import StatusBadge from "../components/StatusBadge.jsx";
import { revenueData, invoices, workLogs} from "../data/mockData.js";
import {
    AreaChart,
    Area,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer,
} from "recharts";
import {
    Plus,
    ReceiptText,
    CheckCircle2,
    Clock3,
    Euro,
    CalendarDays
} from "lucide-react";


function Dashboard() {
    return (
        <div className="min-h-screen bg-slate-50 text-slate-900 flex">

            <main className="flex-1 min-w-0">


                <section className="p-5 lg:p-7 space-y-6">
                    <div className="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-4">
                        <KpiCard title="Total Invoices" value="128" change="+12 this month" icon={ReceiptText} />
                        <KpiCard title="Open Invoices" value="23" change="+5 this month" icon={Clock3} />
                        <KpiCard title="Paid Invoices" value="105" change="+20 this month" icon={CheckCircle2} />
                        <KpiCard title="Total Revenue" value="€24,560" change="+8.5% this month" icon={Euro} />
                    </div>

                    <div className="grid grid-cols-1 xl:grid-cols-5 gap-5">
                        <div className="xl:col-span-3 rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
                            <div className="flex items-center justify-between mb-5 hidden sm:block">
                                <div>
                                    <h2 className="font-bold text-slate-900">Revenue Overview</h2>
                                    <p className="text-sm text-slate-500">Monthly revenue from invoices</p>
                                </div>
                                <button className="text-sm text-slate-500 hover:text-slate-900">This Month</button>
                            </div>
                            <div className="h-72">
                                <div className="hidden sm:block h-72">
                                    <ResponsiveContainer width="100%" height="100%">
                                        <AreaChart data={revenueData} margin={{ top: 10, right: 10, left: 0, bottom: 0 }}>
                                            <defs>
                                                <linearGradient id="revenue" x1="0" y1="0" x2="0" y2="1">
                                                    <stop offset="5%" stopColor="currentColor" stopOpacity={0.18} />
                                                    <stop offset="95%" stopColor="currentColor" stopOpacity={0} />
                                                </linearGradient>
                                            </defs>
                                            <CartesianGrid strokeDasharray="3 3" vertical={false} />
                                            <XAxis dataKey="date" tickLine={false} axisLine={false} tick={{ fontSize: 12 }} />
                                            <YAxis tickLine={false} axisLine={false} tick={{ fontSize: 12 }} tickFormatter={(v) => `€${v / 1000}K`} />
                                            <Tooltip formatter={(value) => [`€${value}`, "Revenue"]} />
                                            <Area type="monotone" dataKey="revenue" stroke="currentColor" strokeWidth={3} fill="url(#revenue)" className="text-blue-600" />
                                        </AreaChart>
                                    </ResponsiveContainer>
                                </div>
                                <div className="sm:hidden bg-white rounded-2xl border border-slate-200 p-4">
                                    <h3 className="font-semibold text-slate-900">
                                        Revenue Overview
                                    </h3>

                                    <p className="text-slate-500 text-sm mt-1">
                                        Monthly revenue from invoices
                                    </p>

                                    <p className="text-3xl font-bold mt-4">
                                        €24,560
                                    </p>

                                    <p className="text-green-600 text-sm mt-1">
                                        +8.5% this month
                                    </p>
                                </div>
                            </div>
                        </div>

                        <div className="xl:col-span-2 rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
                            <div className="flex items-center justify-between mb-5">
                                <div>
                                    <h2 className="font-bold text-slate-900">Recent Invoices</h2>
                                    <p className="text-sm text-slate-500">Latest generated invoices</p>
                                </div>
                                <button className="text-sm text-blue-600 hover:text-blue-700 font-medium">View all</button>
                            </div>
                            <div className="space-y-3">
                                {invoices.map((invoice) => (
                                    <div key={invoice.number} className="grid grid-cols-4 items-center gap-3 text-sm py-2 border-b border-slate-100 last:border-0">
                                        <span className="font-medium text-slate-700">{invoice.number}</span>
                                        <span className="text-slate-600 truncate">{invoice.client}</span>
                                        <span className="text-slate-900 font-semibold text-right">{invoice.amount}</span>
                                        <span className="text-right"><StatusBadge status={invoice.status} /></span>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>

                    <div className="grid grid-cols-1 xl:grid-cols-5 gap-5">
                        <div className="xl:col-span-3 rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
                            <div className="flex items-center justify-between mb-4">
                                <div>
                                    <h2 className="font-bold text-slate-900">Upcoming Work Logs</h2>
                                    <p className="text-sm text-slate-500">Recently planned or created work entries</p>
                                </div>
                                <CalendarDays size={20} className="text-slate-400" />
                            </div>
                            <div className="space-y-3">
                                {workLogs.map((log) => (
                                    <div key={`${log.date}-${log.employee}`} className="grid grid-cols-4 gap-3 items-center text-sm py-3 border-b border-slate-100 last:border-0">
                                        <span className="text-slate-500">{log.date}</span>
                                        <span className="font-medium text-slate-800 truncate max-w-[120px]">{log.object}</span>
                                        <span className="text-slate-600">{log.employee}</span>
                                        <span className="text-right font-semibold text-slate-900">{log.hours}</span>
                                    </div>
                                ))}
                            </div>
                        </div>

                        <div className="xl:col-span-2 rounded-2xl border border-slate-200 bg-white p-5 shadow-sm">
                            <h2 className="font-bold text-slate-900 mb-1">Quick Actions</h2>
                            <p className="text-sm text-slate-500 mb-5">Create core records faster</p>
                            <div className="grid grid-cols-1 sm:grid-cols-3 xl:grid-cols-1 gap-3">
                                <button className="flex items-center justify-center gap-2 rounded-xl border border-blue-200 bg-blue-50 px-4 py-3 text-sm font-semibold text-blue-700 hover:bg-blue-100 transition">
                                    <Plus size={17} /> New Client
                                </button>
                                <button className="flex items-center justify-center gap-2 rounded-xl border border-slate-200 bg-white px-4 py-3 text-sm font-semibold text-slate-700 hover:bg-slate-50 transition">
                                    <Plus size={17} /> New Work Log
                                </button>
                                <button className="flex items-center justify-center gap-2 rounded-xl bg-blue-600 px-4 py-3 text-sm font-semibold text-white hover:bg-blue-700 transition shadow-sm">
                                    <ReceiptText size={17} /> Generate Invoice
                                </button>
                            </div>
                        </div>
                    </div>
                </section>
            </main>
        </div>
    );
}

export default Dashboard;