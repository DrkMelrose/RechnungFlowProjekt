import React from "react";
import { NavLink } from "react-router-dom";
import {
    LayoutDashboard,
    Users,
    Building2,
    UserCog,
    Clock,
    FileText,
    PlusCircle,
    Settings,
} from "lucide-react";

const navItems = [
    { to: "/", label: "Dashboard", icon: LayoutDashboard },
    { to: "/clients", label: "Clients", icon: Users },
    { to: "/objects", label: "Objects", icon: Building2 },
    { to: "/employees", label: "Employees", icon: UserCog },
    { to: "/worklogs", label: "Work Logs", icon: Clock },
    { to: "/invoices", label: "Invoices", icon: FileText },
    { to: "/generate-invoice", label: "Generate Invoice", icon: PlusCircle },
    { to: "/settings", label: "Settings", icon: Settings },
];

export default function Sidebar({isOpen, onClose}) {
    return (
        <>
            {/* Mobile overlay */}
            {isOpen && (
                <div
                    className="fixed inset-0 bg-black/40 z-40 lg:hidden"
                    onClick={onClose}
                />
            )}
            <aside
                className={`
              fixed lg:static inset-y-0 left-0 z-50
              w-64 min-h-screen bg-white border-r border-slate-200
              transform transition-transform duration-300
              ${isOpen ? "translate-x-0" : "-translate-x-full"}
              lg:translate-x-0
            `}>

                {/* Logo */}
                <div className="p-6 text-xl font-bold text-slate-900">
                    RechnungFlow
                </div>

                {/* Navigation */}
                <nav className="px-3 space-y-1">
                    {navItems.map((item) => {
                        const Icon = item.icon;

                        return (
                            <NavLink
                                key={item.to}
                                to={item.to}
                                className={({ isActive }) =>
                                    `flex items-center gap-3 px-4 py-2 rounded-lg text-sm font-medium transition
                    ${
                                        isActive
                                            ? "bg-slate-100 text-slate-900"
                                            : "text-slate-500 hover:bg-slate-50 hover:text-slate-900"
                                    }`
                                }
                            >
                                <Icon size={18} />
                                {item.label}
                            </NavLink>
                        );
                    })}
                </nav>
            </aside>
        </>
    );
}